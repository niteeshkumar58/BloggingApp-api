package com.codewithniteesh.blog.payloads;


import com.codewithniteesh.blog.entities.Category;
import com.codewithniteesh.blog.entities.Comment;
import com.codewithniteesh.blog.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;

@Setter
@Getter
@NoArgsConstructor
public class PostDto {
    private int postId;
    @NotEmpty(message = "Post Title should not be empty !!!")
    private String title;
    @NotEmpty(message = "Post Content should not be empty !!!")
    private String content;
    private String imageName;
    private Date addedDate;
    private UserDto user;
    private CategoryDto category;

    HashSet<CommentDto> commentList = new HashSet<>();
}
