/*
 * The MIT License
 *
 * Copyright 2022 Karate Labs Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.intuit.karate.http;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 *
 * @author pthomas3
 */
public class ProxyResponse {

    public final ProxyContext context;
    public final FullHttpRequest request;
    public final FullHttpResponse response;
    
    public String uri() {
        return request.uri();
    }    

    public ProxyResponse transform(String body) {
        return new ProxyResponse(context, request, HttpUtils.transform(response, body));
    }
    
    public ProxyResponse fake(int status, String body) {
        return new ProxyResponse(context, request, HttpUtils.createResponse(status, body));
    }

    public ProxyResponse(ProxyContext context, FullHttpRequest request, FullHttpResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
    }

    public ProxyResponse header(String key, Object value) {
        if (response != null) {
            response.headers().add(key, value);
        }
        return this;
    }

}
