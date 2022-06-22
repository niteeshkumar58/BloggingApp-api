package com.codewithniteesh.blog.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.websocket.server.ServerEndpoint;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

    private int categoryId;

    @NotEmpty(message = "Type should not be empty !!!")
    private String categoryType;

    @NotEmpty(message = "Description should not be empty !!!")
    private String categoryDescription;
}
