package com.gurin.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gurin.core.bo.CommentService;
import com.gurin.core.bo.PhotoService;
import com.gurin.core.bo. UserInfoService;
import com.gurin.core.bo.UserService;
import com.gurin.core.entities.Photo;
import com.gurin.core.entities.User;
import com.gurin.core.entities.enums.Roles;
import com.gurin.web.customModel.FileUploadForm;
import com.gurin.web.customModel.ProfileForm;
import com.gurin.web.validator.CustomValidator;
import com.gurin.web.validator.FileValidator;
import com.gurin.web.validator.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gurin.utils.Utils.*;
import static com.gurin.utils.service.FileServiceUtils.saveFile;

/**
 * Created by SGurin on 30.03.2016.
 */
@Controller
public class Controllers {

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CustomValidator customValidator;

    @Autowired
    private FileValidator fileValidator;

    @Autowired
    private ProfileValidator profileValidator;

    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "birthday", new CustomDateEditor(dateFormat, true));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcomePage() throws IOException {

        ModelAndView model = new ModelAndView();
        List<Photo> slidePhotos = photoService.getRandomPhotos(5);
        encodedPhotoByPath(slidePhotos);

        model.addObject("slidePhotos", slidePhotos);
        model.setViewName("home");
        return model;

    }

    @RequestMapping(value = {"/gallery"}, method = RequestMethod.GET)
    public ModelAndView galleryPage() throws IOException {
        ModelAndView model = new ModelAndView();

        List<Photo> allPhotos = photoService.getAllPhotosSortByDate();
        encodedPhotoByPath(allPhotos);
        photoService.calculateTotalPointsByPhotoId(allPhotos);

        Map<String, Object> allObjects = new HashMap<>();
        allObjects.put("allPhotos", new ObjectMapper().writeValueAsString(allPhotos));
        allObjects.put("allCategories", new ObjectMapper().writeValueAsString(getAllCategoriesWithAll()));
        allObjects.put("currentCategoryId", getIdCategoryByValue("Все"));
        allObjects.put("title", "Все");

        model.addAllObjects(allObjects);
        model.setViewName("gallery");

        return model;

    }

    @RequestMapping(value = {"/id{id}"}, method = RequestMethod.GET)
    public ModelAndView userMain(@PathVariable("id") Integer id) throws IOException {
        ModelAndView model = new ModelAndView();

        List<Photo> allPhotosByUser = photoService.getAllPhotosByUserIdSortByDate(id);
        encodedPhotoByPath(allPhotosByUser);
        photoService.calculateTotalPointsByPhotoId(allPhotosByUser);

        User user = userService.findUserById(id);
        if (user.getUserInfo() != null) {
            encodedUserInfoAvatar(user.getUserInfo());
        }

        Map<String, Object> allObjects = new HashMap<>();
        allObjects.put("allPhotosByUser", new ObjectMapper().writeValueAsString(allPhotosByUser));
        allObjects.put("user", user);
        allObjects.put("title", "Фотограф: ");

        model.addAllObjects(allObjects);
        model.setViewName("user_main");

        return model;
    }

    @RequestMapping(value = {"/id{id}/profile"}, method = RequestMethod.GET)
    public ModelAndView getProfile(@PathVariable("id") Integer id) throws IOException {

        ModelAndView model = new ModelAndView();

        User user = userService.findUserById(id);
        if (!isEmpty(user.getUserInfo())) {
            encodedUserInfoAvatar(user.getUserInfo());
        }

        Map<String, Object> allObjects = new HashMap<>();
        allObjects.put("user", user);
        allObjects.put("id", id);
        allObjects.put("profileForm", new ProfileForm());
        allObjects.put("iAm", isCurrentUser(user));
        allObjects.put("pointAvg", photoService.getAvgPointsAllPhotosByUserId(user.getId()));

        model.addAllObjects(allObjects);
        model.setViewName("user_profile");

        return model;
    }

    @RequestMapping(value = "/id{id}/profile", method = RequestMethod.POST)
    public ModelAndView setProfile(@ModelAttribute("profileForm") ProfileForm profileForm, @PathVariable("id") Integer id, BindingResult binding, Model model) {
        profileValidator.validate(profileForm, binding);

        User currentUser = userService.findUserById(id);

        if (binding.hasErrors()) {
            Map<String, Object> allObjects = new HashMap<>();
            allObjects.put("profileForm", profileForm);
            allObjects.put("iAm", isCurrentUser(currentUser));

            model.addAllAttributes(allObjects);

            return new ModelAndView("user_profile");
        }

        if (!userService.changeProfile(currentUser, profileForm)) {
            return new ModelAndView("user_profile");
        }

        return new ModelAndView("redirect:/id" + id);
    }

    @RequestMapping(value = {"/gallery/category/{id}"}, method = RequestMethod.GET)
    public ModelAndView category(@PathVariable("id") Integer id) throws IOException {
        ModelAndView model = new ModelAndView();
        List<Photo> photoByCategory = null;

        String category = getAllCategoriesWithAll().get(id);

        if (category.equals("Все")) {
            photoByCategory = photoService.getAllPhotosSortByDate();
        } else {
            photoByCategory = photoService.getPhotosByCategory(category.toString());
        }

        encodedPhotoByPath(photoByCategory);
        photoService.calculateTotalPointsByPhotoId(photoByCategory);

        Map<String, Object> allObjects = new HashMap<>();
        allObjects.put("allPhotos", new ObjectMapper().writeValueAsString(photoByCategory));
        allObjects.put("allCategories", new ObjectMapper().writeValueAsString(getAllCategoriesWithAll()));
        allObjects.put("currentCategoryId", id);
        allObjects.put("title", category);

        model.addAllObjects(allObjects);
        model.setViewName("gallery");

        return model;
    }

    @RequestMapping(value = {"/gallery/category/{id}/sort/popularity"}, method = RequestMethod.GET)
    public ModelAndView categoryByPopularity(@PathVariable("id") Integer id) throws IOException {
        ModelAndView model = new ModelAndView();
        List<Photo> photoByCategory = null;

        String category = getAllCategoriesWithAll().get(id);

        if (category.equals("Все")) {
            photoByCategory = photoService.getAllPhotosSortByPopularity();
        } else {
            photoByCategory = photoService.getPhotosByCategoryAndPopularity(category.toString());
        }

        encodedPhotoByPath(photoByCategory);
        photoService.calculateTotalPointsByPhotoId(photoByCategory);

        Map<String, Object> allObjects = new HashMap<>();
        allObjects.put("allPhotos", new ObjectMapper().writeValueAsString(photoByCategory));
        allObjects.put("allCategories", new ObjectMapper().writeValueAsString(getAllCategoriesWithAll()));
        allObjects.put("currentCategoryId", id);
        allObjects.put("title", category);
        allObjects.put("byPopularity", true);

        model.addAllObjects(allObjects);
        model.setViewName("gallery");

        return model;
    }

    @RequestMapping(value = {"/gallery/sort/popularity"}, method = RequestMethod.GET)
    public ModelAndView category() throws IOException {
        ModelAndView model = new ModelAndView();

        List<Photo> allPhotos = photoService.getAllPhotosSortByPopularity();
        encodedPhotoByPath(allPhotos);

        Map<String, Object> allObjects = new HashMap<>();
        allObjects.put("allPhotos", new ObjectMapper().writeValueAsString(allPhotos));
        allObjects.put("allCategories", new ObjectMapper().writeValueAsString(getAllCategoriesWithAll()));
        allObjects.put("currentCategoryId", getIdCategoryByValue("Все"));
        allObjects.put("title", "Все");
        allObjects.put("byPopularity", true);

        model.addAllObjects(allObjects);
        model.setViewName("gallery");

        return model;
    }

    @RequestMapping(value = {"/photo/{id}"}, method = RequestMethod.GET)
    public ModelAndView photo(@PathVariable("id") Integer id) throws IOException {

        ModelAndView model = new ModelAndView();

        Photo photo = photoService.findPhotoById(id);
        encodedPhotoByPath(photo);
        photoService.calculateTotalPointsByPhotoId(photo);
        photoService.hasPhotoAlreadyEvaluatedByCurrentUser(photo);

        Map<String, Object> allObjects = new HashMap<>();
        allObjects.put("photo", photo);
        allObjects.put("currentUser", getCurrentUser());

        model.addAllObjects(allObjects);
        model.setViewName("photo");

        return model;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "JustPhoto");
        model.addObject("message", "This is protected page!");
        model.setViewName("admin");
        return model;

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }
/*
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
*/
        model.setViewName("login");

        return model;

    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getRegister() {

        ModelAndView model = new ModelAndView();
        model.setViewName("register");
        model.addObject("user", new User());
        return model;

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String setRegister(@ModelAttribute("user") @Valid User user, BindingResult binding, Model model) {
        customValidator.validate(user, binding);
        if (binding.hasErrors()) {
            model.addAttribute("user", user);

            return "register";
        }
        user.setRole(Roles.USER.name());
        user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), null));

        userService.createUser(user);

        return "redirect:/login";

    }

    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {

        ModelAndView model = new ModelAndView();

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            model.addObject("name", userDetails.getUsername());
        }

        model.setViewName("403");
        return model;

    }

    //http://javapointers.com/tutorial/how-to-upload-file-in-spring-mvc/
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView getUploadFile() {
        ModelAndView model = new ModelAndView();

        Map<String, Object> allObjects = new HashMap<>();
        allObjects.put("uploadForm", new FileUploadForm());
        allObjects.put("allCategories", getAllCategories());

        model.addAllObjects(allObjects);
        model.setViewName("upload");

        return model;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView setUploadFile(@ModelAttribute("uploadForm") FileUploadForm uploadForm, BindingResult binding, Model model) {
        fileValidator.validate(uploadForm, binding);

        if (binding.hasErrors()) {
            model.addAttribute("uploadForm", uploadForm);

            return new ModelAndView("upload");
        }

        String prefix = getRandomPhotoName();
        MultipartFile multipartFile = uploadForm.getFile();

        String email = getCurrentUser().getEmail();

        if (saveFile(multipartFile, email, prefix)) {
            String fileName = prefix + multipartFile.getOriginalFilename();
            photoService.savePhoto(uploadForm, email, fileName);

            return new ModelAndView("redirect:gallery");
        }

        return new ModelAndView("upload");
    }

}

