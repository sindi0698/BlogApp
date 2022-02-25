import {generateBlog, generateBlogSearch} from "./BlogPageHelper.js";

window.addEventListener('DOMContentLoaded', async (event) => {
    await generateBlog();

    document.getElementById("searchButton").addEventListener("click", async (event) => {
        event.preventDefault();
        await generateBlogSearch();
    });
});
