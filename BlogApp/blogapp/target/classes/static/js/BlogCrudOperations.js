import {STATUS_CODES} from "./StatusCodeConstants.js";

async function saveBlogPost() {
    let title = document.getElementById("blogTitle").value;
    let category = document.querySelector('input[name="blogCategory"]:checked').value;
    let content = document.getElementById("blogContent").value;

    if (checkEmpty(title, content)) {
        let newBlog = {
            blogTitle: title,
            blogCategory: {id: category},
            blogContent: content
        };
        let response = await fetch('/api/posts/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newBlog)
        });
        const body = await response.json();
        if (body.statusCode === STATUS_CODES.CREATED) {
            window.location.replace("/");
        } else {
            document.getElementById("error-message").innerHTML = body.message;
            $("#error-modal").modal('show');
        }
    }
}

async function getAllBlogPosts() {
    let response = await fetch('/api/posts');
    const body = await response.json();
    if (response.ok) {
        return body.responseObject;
    } else {
        document.getElementById("error-message").innerHTML = body.message;
        $("#error-modal").modal('show');
    }
}

async function editBlogPost(blogId) {
    let title = document.getElementById("blogTitle").value;
    let category = document.querySelector('input[name="blogCategory"]:checked').value;
    let content = document.getElementById("blogContent").value;
    if (checkEmpty(title, content)) {
        let newBlog = {
            blogId: blogId,
            blogTitle: title,
            blogCategory: {id: category},
            blogContent: content
        };
        let response = await fetch('/api/posts/', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newBlog)
        });
        const body = await response.json();
        if (body.statusCode === STATUS_CODES.OK) {
            window.location.replace("/");
        } else {
            document.getElementById("error-message").innerHTML = body.message;
            $("#error-modal").modal('show');        }
    }
}

async function deleteBlogPost(id) {
   let response = await fetch(`/api/posts/${id}`, {
        method: "DELETE"
    });
    const body = await response.json();
    if (body.statusCode === STATUS_CODES.OK) {
        window.location.replace("/");
    } else {
        document.getElementById("error-message").innerHTML = body.message;
        $("#error-modal").modal('show');
    }
}

function checkEmpty(blogTitle, blogContent) {
    if (blogTitle !== "" && blogContent !== "") {
        return true;
    } else {
        alert(`Please fill both title and content fields.`);
        return false;
    }
}

export {saveBlogPost, getAllBlogPosts, editBlogPost, deleteBlogPost, checkEmpty}