/*
 * Generated by XDoclet - Do not edit!
 */
package com.endlessloopsoftware.egonet.interfaces;

/**
 * Local home interface for ConfigurationSB.
 * @copyright 2004 Endless Loop Software Inc.
 */
public interface ConfigurationSBLocalHome
   extends com.endlessloopsoftware.egonet.interfaces.EgoSBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/ConfigurationSBLocal";
   public static final String JNDI_NAME="ConfigurationSBLocal";

   public com.endlessloopsoftware.egonet.interfaces.ConfigurationSBLocal create()
      throws javax.ejb.CreateException;

}