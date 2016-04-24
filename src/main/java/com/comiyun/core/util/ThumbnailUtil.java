package com.comiyun.core.util;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ThumbnailUtil {
    public static Logger logger = LoggerFactory.getLogger(ThumbnailUtil.class);

    public static void thumb(File src, File target, int width, int height) {
        ScaleParameter scaleParam = new ScaleParameter(width, height);
        InputStream inStream = null;
        OutputStream outStream = null;
        ImageRender wr = null;
        try {
            inStream = new FileInputStream(src);
            outStream = new FileOutputStream(target);
            ImageRender rr = new ReadRender(inStream);
            ImageRender sr = new ScaleRender(rr, scaleParam);
            wr = new WriteRender(sr, outStream);
            wr.render(); // 触发图像处理
        } catch (Exception e) {
            logger.error("thumbnail image error::", e);
        } finally {
            IOUtils.closeQuietly(inStream); // 图片文件输入输出流必须记得关闭
            IOUtils.closeQuietly(outStream);
            if (wr != null) {
                try {
                    wr.dispose(); // 释放simpleImage的内部资源
                } catch (SimpleImageException ignore) {
                    // skip ...
                }
            }
        }
    }

    public static void main(String[] args) {
        thumb(new File("d:/a.png"), new File("d:/b.png"), 400, 100);
    }

}
