/*
 * jQuery File Upload Plugin JS Example
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/* global $, window */

$(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        url: 'http://localhost:8080/upload/Img',
    });
    $("#fileupload").bind("fileuploaddone", function (e, data) {
        var imgs = $("#imgs").val();
        //
        if(imgs!=""){
        imgs = imgs+","+data._response.result[0].url;
        $("#imgs").val(imgs);
        }else{
            imgs = imgs+data._response.result[0].url;
            $("#imgs").val(imgs);
        }
    });
    $('#fileupload')
        .bind('fileuploaddestroy', function (e, data) {
            var pic = data.context[0].cells[1].childNodes[1].childNodes[1].href;
            var imgs = $("#imgs").val();
            if(imgs.length>pic.length){
                imgs = imgs.replace(","+pic,"");
                imgs = imgs.replace(pic,"")
            }else{
                imgs = imgs.replace(pic,"")
            }
            $("#imgs").val(imgs);
        });
    // Enable iframe cross-domain access via redirect option:
    $('#fileupload').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );
    // Initialize the jQuery File Upload widget:
    $('#fileupload2').fileupload({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        url: 'http://localhost:8080/upload/Img'
    });
    $("#fileupload2").bind("fileuploaddone", function (e, data) {
        var imgs = $("#imgs2").val();
        //
        if(imgs!=""){
            imgs = imgs+","+data._response.result[0].url;
            $("#imgs2").val(imgs);
        }else{
            imgs = imgs+data._response.result[0].url;
            $("#imgs2").val(imgs);
        }
    });
    $('#fileupload2')
        .bind('fileuploaddestroy', function (e, data) {
            var pic = data.context[0].cells[1].childNodes[1].childNodes[1].href;
            var imgs = $("#imgs2").val();
            if(imgs.length>pic.length){
                imgs = imgs.replace(","+pic,"");
                imgs = imgs.replace(pic,"")
            }else{
                imgs = imgs.replace(pic,"")
            }
            $("#imgs2").val(imgs);
        });
    // Enable iframe cross-domain access via redirect option:
    $('#fileupload2').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );
    if (window.location.hostname === 'blueimp.github.io') {
        // Demo settings:
        $('#fileupload').fileupload('option', {
            url: '//jquery-file-upload.appspot.com/',
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),
            maxFileSize: 999000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
        });
        // Upload server status check for browsers with CORS support:
        if ($.support.cors) {
            $.ajax({
                url: '//jquery-file-upload.appspot.com/',
                type: 'HEAD'
            }).fail(function () {
                $('<div class="alert alert-danger"/>')
                    .text('Upload server currently unavailable - ' +
                            new Date())
                    .appendTo('#fileupload');
            });
        }
    } else {
        // Load existing files:
        $('#fileupload').addClass('fileupload-processing');
        $.ajax({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            url: $('#fileupload').fileupload('option', 'url'),
            dataType: 'json',
            context: $('#fileupload')[0]
        }).always(function () {
            $(this).removeClass('fileupload-processing');
        }).success(function (result) {
            console.info(result);
            $(this).fileupload('option', 'done')
                .call(this, $.Event('done'), {result: result});
        });
    }

});
