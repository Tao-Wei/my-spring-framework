package com.tw.springframework.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 网络资源加载
 */
public class UrlResource implements Resource {
    private String path;
    private URL url;

    public UrlResource(String path) throws MalformedURLException {

        this.url=new URL(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return url.openConnection().getInputStream();
    }
}
