CREATE TABLE BlogPost
(
    blog_id          SERIAL PRIMARY KEY,
    blog_title       VARCHAR(256) NOT NULL UNIQUE,
    blog_content     TEXT         NOT NULL,
    date_stamp       DATE         NOT NULL DEFAULT CURRENT_DATE,
    blog_author      VARCHAR(50)  NOT NULL,
    blog_category_id INT          NOT NULL,
    FOREIGN KEY (blog_category_id) REFERENCES BlogCategory (category_id)
);