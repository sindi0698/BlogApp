import {getAllBlogPosts, deleteBlogPost} from "./BlogCrudOperations.js";
import {STATUS_CODES} from "./StatusCodeConstants.js";

async function generateBlog() {
    let blogPosts = await getAllBlogPosts();
    populateBody(blogPosts);
}

async function generateBlogSearch() {
    if (window.location.pathname !== "/blog.html") {
        window.location.href = "/blog.html";
    }
    let blogPosts = await search();
    populateBody(blogPosts);
}

function populateBody(blogPosts) {
    let blogBody = document.getElementById("blog_body");
    blogBody.innerHTML = "";

    if(blogPosts.length === 0){
        blogBody.innerHTML +=
            `<div class="d-flex justify-content-center text-danger">
                <h2><b><i>There are no matching search results.</i></b></h2>
            </div>`;
    }

    blogPosts.forEach(function (post) {
        let blogCard = `
        <div class="container my-2 p-5 blogpost rounded"><div><h2>${post.blogTitle}</h2></div>
            <div><h4><b>${post.blogAuthor}</b></h4></div>
            <p><div><i>Category: ${post.blogCategory.name}</i></div></p>
            <div><p><h4>${post.blogContent}</h4></p></div>
            <div class="d-flex justify-content-end">
            <button id='${post.blogId}' class='btn btn-warning edit-blog mx-2'>Edit</button>
            <button id='${post.blogId}' class='btn btn-danger remove-blog mx-2'>Delete</button>
            </div>
        </div><br>`;
        blogBody.innerHTML += blogCard;
    });

    editButtonListeners();
    deleteButtonListeners();
}

function editButtonListeners() {
    let editBtnArray = document.getElementsByClassName("btn btn-warning edit-blog mx-2");
    if (!!editBtnArray) {
        for (let i = 0; i < editBtnArray.length; i++) {
            let editBtn = editBtnArray[i];
            editBtn.addEventListener("click", function (event) {
                event.preventDefault();
                editButtonListener(editBtn.id);
            });
        }
    }
}

function editButtonListener(id) {
    window.location.href = `/new-blog.html?id=${id}`;
}

function deleteButtonListeners() {
    let deleteBtnArray = document.getElementsByClassName("btn btn-danger remove-blog mx-2");
    if (!!deleteBtnArray) {
        for (let i = 0; i < deleteBtnArray.length; i++) {
            let deleteBtn = deleteBtnArray[i];
            deleteBtn.addEventListener("click", async function (event) {
                event.preventDefault();
                await deleteButtonListener(deleteBtn.id);
            });
        }
    }
}

async function deleteButtonListener(id) {
    let result = confirm("Do you want to delete this task?");
    if (result) {
        await deleteBlogPost(id);
        location.reload();
    }
}

async function getBlogPostById(id) {
    let response = await fetch(`/api/post/${id}`);
    const body = await response.json();
    if (body.statusCode === STATUS_CODES.OK) {
        return body.responseObject;
    } else {
        document.getElementById("error-message").innerHTML = body.message;
        $("#error-modal").modal('show');
    }
}

async function search(){
    let keyword = document.getElementById("keyword").value.toLowerCase();
    let categoryOption = document.getElementById("blogCategory");
    let categoryId = parseInt(categoryOption.options[categoryOption.selectedIndex].value);
    let response = await fetch(`/api/search?filteredCategoryId=${categoryId}&filteredTitle=${keyword}`);
    const body = await response.json();
    if (body.statusCode === STATUS_CODES.OK) {
        return body.responseObject;
    } else {
        document.getElementById("error-message").innerHTML = body.message;
        $("#error-modal").modal('show');
    }
}

export {getAllBlogPosts, generateBlog, getBlogPostById, generateBlogSearch}
