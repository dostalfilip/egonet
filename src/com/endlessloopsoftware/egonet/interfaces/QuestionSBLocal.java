/*
 * Generated by XDoclet - Do not edit!
 */
package com.endlessloopsoftware.egonet.interfaces;

/**
 * Local interface for QuestionSB.
 * @copyright 2004 Endless Loop Software Inc.
 */
public interface QuestionSBLocal
   extends com.endlessloopsoftware.egonet.interfaces.EgoSBLocal
{

   public com.endlessloopsoftware.egonet.interfaces.QuestionEJBLocal findByPrimaryKey( com.endlessloopsoftware.egonet.interfaces.QuestionEJBPK pk ) throws javax.ejb.FinderException, javax.naming.NamingException;

   public com.endlessloopsoftware.egonet.util.QuestionDataValue findDataByPrimaryKey( com.endlessloopsoftware.egonet.interfaces.QuestionEJBPK pk ) throws javax.ejb.FinderException, javax.naming.NamingException;

   public com.endlessloopsoftware.egonet.util.QuestionDataValue createEntity( com.endlessloopsoftware.egonet.util.QuestionDataValue data,java.lang.Long studyId ) throws javax.ejb.CreateException, javax.naming.NamingException;

   public void removeEntity( com.endlessloopsoftware.egonet.util.QuestionDataValue data ) throws javax.ejb.RemoveException, javax.ejb.FinderException, javax.naming.NamingException;

}