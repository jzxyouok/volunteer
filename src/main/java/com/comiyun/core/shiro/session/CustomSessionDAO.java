package com.comiyun.core.shiro.session;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * 自定义SessionDAO
 *
 * @author ydwcn
 * @ClassName: CustomSessionDAO
 * @date 2014-6-23 下午2:32:04
 */
public class CustomSessionDAO extends AbstractSessionDAO {

    Logger logger = LoggerFactory.getLogger(CustomSessionDAO.class);

    private Cache cache;

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session或session id为空...");
            return;
        }
        Element e = new Element(session.getId(), session);
        cache.put(e);
        logger.debug("保存session：{}", session.getId());
    }

    @Override
    public void delete(Session session) {
        Serializable id = session.getId();
        cache.remove(id);
        logger.debug("删除session：{}", session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }

    @Override
    protected Serializable doCreate(Session session) {
        return null;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return null;
    }
}
