package com.capgemini.bookstore.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public final class Util {
    private Util() {}

    public static URI createLocationURI(Integer id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    public static URI createLocationURIfromContext(String path) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(path)
                .build().toUri();
    }

    public static URI createLocationURIfromContext(String path, Integer id) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(path)
                .buildAndExpand(id)
                .toUri();
    }

}
