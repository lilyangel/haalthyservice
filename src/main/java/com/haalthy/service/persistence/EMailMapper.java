package com.haalthy.service.persistence;

import com.haalthy.service.domain.EMail;

import java.util.List;

/**
 * Created by Ken on 2016-01-04.
 */
public interface EMailMapper {
    public int insertEmail(EMail eMail);

    public int updateMail(EMail eMail);

    public List<EMail> getEmails();
}
