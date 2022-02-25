import {generateBlogSearch, getBlogPostById} from "./BlogPageHelper.js";
import {saveBlogPost, editBlogPost} from "./BlogCrudOperations.js";

window.addEventListener('DOMContentLoaded', async (event) => {
    event.preventDefault();

    let blogId = getParameterByName('id');
    if (!!blogId) {
        await showBlogPost(blogId);
        document.getElementById("add-blog").addEventListener("click", async (e) => {
            e.preventDefault();
            await editBlogPost(parseInt(blogId));
        });
    }else {
        document.getElementById("add-blog").addEventListener("click", async (e) => {
            e.preventDefault();
            await saveBlogPost();
        });
    }

    document.getElementById("searchButton").addEventListener("click", (event) => {
        event.preventDefault();
        generateBlogSearch();
    });
});

function getParameterByName(name, url = window.location.href) {
    name = name.replace(/[\[\]]/g, '\\$&');
    let regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

async function showBlogPost(blogId) {
    let blogPost = await getBlogPostById(blogId);
    let title = blogPost.blogTitle;
    let category = blogPost.blogCategory.name;
    let content = blogPost.blogContent;
    document.getElementById("blogTitle").value = title;
    document.getElementById("blogContent").value = content;
    document.getElementById(category).checked = true;
}

export {getParameterByName};