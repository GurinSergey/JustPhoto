function drawGalleryBody(list, title, byPopularity, categoryId, listCat, byUser) {
    drawPhotos(list, title, byPopularity, categoryId, byUser);
    drawCategories(listCat, categoryId, byPopularity);
}

function drawPhotos(list, title, byPopularity, categoryId, byUser) {
    var name = title;
    if (title == "Все") {
        name = "Фотографии";
    }

    var parent = document.getElementById('all_photo');
    var title = byUser == true ? document.createElement('h3') : document.createElement('h1');
    title.setAttribute('class', 'title');
    title.innerHTML = name;

    parent.appendChild(title);

    if (byUser != true) {
        var sortDiv = document.createElement('div');
        sortDiv.setAttribute('id', 'sort');
        sortDiv.innerHTML = "Сортировать по ";

        var sortA = document.createElement('a');
        if (byPopularity) {
            sortA.setAttribute('href', categoryId != null ? '/gallery/category/' + categoryId : '/gallery');
            sortA.innerHTML = "дате";
        }
        else {
            sortA.setAttribute('href', categoryId != null ? '/gallery/category/' + categoryId + '/sort/popularity' : '/gallery/sort/popularity');
            sortA.innerHTML = "популярности";
        }

        sortDiv.appendChild(sortA);
        parent.appendChild(sortDiv);
    }

    if (list.length > 0) {
        list.forEach(function (node) {

                var photoDiv = document.createElement('div');
                photoDiv.setAttribute('class', 'photo');

                var imageDiv = document.createElement('div');
                imageDiv.setAttribute('class', 'image');

                var imageA = document.createElement('a');
                imageA.setAttribute('href', '/photo/' + node.id);

                var imageImg = document.createElement('img');
                imageImg.setAttribute('src', 'data:image/jpg;base64,' + node.encoded);
                imageImg.setAttribute('title', node.description);

                imageA.appendChild(imageImg);
                imageDiv.appendChild(imageA);

                var infoDiv = document.createElement('div');
                infoDiv.setAttribute('class', 'info');

                var stateDiv = document.createElement('div');
                stateDiv.setAttribute('class', 'state');

                var dateSpan = document.createElement('span');
                dateSpan.setAttribute('class', 'date');
                dateSpan.innerHTML = node.createDate + '&nbsp';

                if (node.comment.length > 0) {
                    var commentsSpan = document.createElement('span');
                    commentsSpan.setAttribute('class', 'comment-cnt');

                    var iCommentsSpan = document.createElement('i');
                    //iCommentsSpan.setAttribute('class', 'fa fa-comment fa-1x');
                    iCommentsSpan.setAttribute('class', 'icon-comments');

                    commentsSpan.appendChild(iCommentsSpan);
                    commentsSpan.appendChild(document.createTextNode(node.comment.length + ' '));
                }

                var scoreSpan = document.createElement('span');
                scoreSpan.setAttribute('class', 'score');
                scoreSpan.innerHTML = (node.totalPoints == 0) ? node.totalPoints : "+" + node.totalPoints;

                stateDiv.appendChild(dateSpan);
                if (node.comment.length > 0) {
                    stateDiv.appendChild(commentsSpan);
                }
                stateDiv.appendChild(scoreSpan);

                var nameDiv = document.createElement('div');
                nameDiv.setAttribute('class', 'name');

                var nameA = document.createElement('a');
                nameA.setAttribute('href', '/photo/' + node.id);
                nameA.innerHTML = node.name + '&nbsp';

                nameDiv.appendChild(nameA);

                var aboutDiv = document.createElement('div');
                aboutDiv.setAttribute('class', 'about');

                var aboutA = document.createElement('a');
                aboutA.setAttribute('href', '/id' + node.user.id);
                aboutA.innerHTML = node.user.username;

                aboutDiv.appendChild(aboutA);

                infoDiv.appendChild(stateDiv);
                infoDiv.appendChild(nameDiv);
                infoDiv.appendChild(aboutDiv);

                photoDiv.appendChild(imageDiv);
                photoDiv.appendChild(infoDiv);

                parent.appendChild(photoDiv);

            }
        )
    }
    else {
        var emptyH1 = document.createElement('h1');
        emptyH1.setAttribute('class', 'empty');

        var epmtyI = document.createElement('i');
        epmtyI.setAttribute('class', 'fa fa-info-circle fa-lg');

        emptyH1.appendChild(epmtyI);
        emptyH1.appendChild(document.createTextNode(" Фотографий не найдено."));

        parent.appendChild(emptyH1);
    }

}

function drawCategories(map, id, byPopularity) {
    var parent = document.getElementById('categories');
    var h5 = document.createElement('h5');
    h5.innerHTML = "Категории:";

    parent.appendChild(h5);

    for (var i in map) {
        var h4 = document.createElement('h4');
        h4.setAttribute('id', 'category' + i);
        h4.setAttribute('class', 'category');

        var categoryA = document.createElement('a');
        if (byPopularity) {
            categoryA.setAttribute('href', '/gallery/category/' + i + '/sort/popularity');
        }
        else {
            categoryA.setAttribute('href', '/gallery/category/' + i);
        }
        categoryA.innerHTML = map[i];

        h4.appendChild(categoryA);

        parent.appendChild(h4);

        if (i == id) {
            $('#category' + i.toString()).addClass('category-selected');
//            $('#category' + i.toString()).attr('disabled', 'disabled');
        }
    }
}

function drawComment(parent, comment, isAuthorized) {
    var containerDiv = document.createElement('div');

    containerDiv.setAttribute('class', 'comment-container');
    containerDiv.setAttribute('id', comment.id);

    var titleDiv = document.createElement('div');
    titleDiv.setAttribute('class', 'comment-title');

    var titleA = document.createElement('a');
    titleA.setAttribute('class', 'nick');
    titleA.setAttribute('href', '/id' + comment.user.id);
    titleA.innerHTML = comment.user.username;

    var titleSpan = document.createElement('span');
    titleSpan.setAttribute('class', 'date');
    titleSpan.innerHTML = '&nbsp' + comment.createDate;

    titleDiv.appendChild(titleA);
    titleDiv.appendChild(titleSpan);
    containerDiv.appendChild(titleDiv);

    var contentDiv = document.createElement('div');
    contentDiv.setAttribute('class', 'comment-content');
    contentDiv.innerHTML = comment.content;

    if (isAuthorized) {
        var answerDiv = document.createElement('div');
        answerDiv.setAttribute('id', 'answer-href');

        var answerA = document.createElement('a');
        answerA.setAttribute('href', 'javascript:redrawInput(' + comment.id + ')');
        answerA.innerHTML = "Ответить";

        answerDiv.appendChild(answerA);
        contentDiv.appendChild(answerDiv);
    }

    containerDiv.appendChild(titleDiv);
    containerDiv.appendChild(contentDiv);

    if (parent.id == 'comments') {
        parent.appendChild(containerDiv);
    } else {
        insertAfter(parent, containerDiv);
    }
//
//    if (comment.childOrder > 0) {
//        $('#' + comment.id.toString()).addClass('margin-left-' + comment.childOrder);
//    }
}

function childIndex(elementId) {
    var i = 0;
    var element = document.getElementById(elementId);
    while (element.parentNode.children[i] != element)i++;
    return i;
}

function insertAfter(someElement, newElement) {
    someElement.parentNode.insertBefore(newElement, someElement.nextSibling);
}

function addComment(currentUserId, photoId) {
    var parentId = document.getElementById("inputBox").parentNode.id;

    var data = {}
    data["parent_id"] = parentId == 'input' ? 0 : parentId;
    data["content"] = $("#content").val();
    data["user_id"] = currentUserId;
    data["photo_id"] = photoId;

    if ($("#content").val() != "") {
        $.ajax({
            type: "POST",
            url: "/addComment",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(data),
            success: function (comment) {
                $("#content").val('');
                redrawInput("input");

                var parentNode = document.getElementById(data.parent_id == 0 ? 'comments' : data.parent_id);
                drawComment(parentNode, comment, true);
            },
            error: function () {
                alert('error ' + JSON.stringify(data));
            }
        })
    }
}

document.onmouseup = function () {
    if (document.getElementById('content') != document.activeElement) {
        if ($("#content").val() == "") {
            var element = document.getElementById("inputBox");
            $("#inputBox").remove();

            var parentNode = document.getElementById("input");

            parentNode.appendChild(element);
        }
    }
}

function drawInput(currentUserId, photoId) {
    var parentNode = document.getElementById("input");

    var inputDiv = document.createElement('div');
    inputDiv.setAttribute('id', 'inputBox');

    var table = document.createElement('table');
    var tr1 = document.createElement('tr');
    var td1 = document.createElement('td');

    var textareaTd1 = document.createElement('textarea');
    textareaTd1.setAttribute('class', 'inputBox');
    textareaTd1.setAttribute('id', 'content');

    td1.appendChild(textareaTd1);
    tr1.appendChild(td1);
    table.appendChild(tr1);

    var tr2 = document.createElement('tr');
    var td2 = document.createElement('td');
    td2.setAttribute('align', 'right');

    var input = document.createElement('input');
    input.setAttribute('class', 'btn-add');
    input.setAttribute('type', 'submit');
    input.setAttribute('onClick', 'addComment(' + currentUserId + ',' + photoId + ')');
    input.setAttribute('value', 'Добавить');

    td2.appendChild(input);
    tr2.appendChild(td2);
    table.appendChild(tr2);

    inputDiv.appendChild(table);

    parentNode.appendChild(inputDiv);
}

function redrawInput(parent) {
    var element = document.getElementById("inputBox");
    $("#inputBox").remove();

    var parentNode = document.getElementById(parent.toString());

    parentNode.appendChild(element);
    $("#content").focus();
}

function formCase(cnt) {
    function createCase() {
        var begin = cnt.toString();
        var end = begin.substr(-1).match('1') ? 'й' : begin.substr(-1).match('2|3|4') ? 'я' : 'ев';

        return (begin + " комментари" + end).toString();
    }

    return parseInt(cnt) ? createCase() : 'Нет комментариев.';
}

function showCountComments(parent, cnt) {
    var titleP = document.createElement('p');
    titleP.setAttribute('class', 'count-comments');

    titleP.innerHTML = formCase(cnt);

    parent.appendChild(titleP);
}

function drawComments(currentUserId, photoId, isAuthorized) {
    $.ajax({
        type: "POST",
        url: "/drawComments",
        data: "id=" + photoId.toString(),
        success: function (list) {
            $("#comments").html("");
            var parent = document.getElementById('comments');
            if (list.length > 0) {
                showCountComments(parent, list.length);
                list.forEach(function (node) {
                    drawComment(parent, node, isAuthorized);
                });
            }
            else {
                showCountComments(parent, null);
            }
        },
        error: function () {
            alert('error ' + photoId);
        }
    })
}

function estimate(point, photoId) {
    var data = {}
    data["points"] = point;
    data["photo_id"] = photoId;

    $.ajax({
        type: "POST",
        url: "/evaluation",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(data),
        success: function (total) {
            $("#star-rating").html('<b class="point"> + ' + data.points + '</b>');
            $("#total").html(total);
        },
        error: function () {
            alert('error ' + JSON.stringify(data));
        }
    })
}

function chooseItem(item) {
    $('#' + item).addClass('profile-item-selected');
}

function openProfilePage(url) {
    $.ajax({
        type: "POST",
        url: "/getProfileURL",
        success: function (id) {
            window.open("/id" + id.toString() + "/profile", "_self");
            return false;
        },
        error: function () {
            alert('error ');
        }
    })
}

function displayInput() {
    $('#file-id').toggle('input-show');
}


function removeElement(e) {
    e.parentNode.removeChild(e);
}

function deleteAvatar(id) {
    $.ajax({
        type: "POST",
        url: "/deleteAvatar",
        data: "id=" + id,
        success: function () {
            $('#avatar').attr('src', '');
        },
        error: function () {
            alert('error ');
        }
    })
}
