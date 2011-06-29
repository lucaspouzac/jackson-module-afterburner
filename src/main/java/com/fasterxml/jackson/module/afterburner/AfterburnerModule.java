package com.fasterxml.jackson.module.afterburner;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;

import com.fasterxml.jackson.module.afterburner.ser.SerializerModifier;
import com.fasterxml.jackson.module.afterburner.deser.DeserializerModifier;

public class AfterburnerModule extends SimpleModule
{
    // TODO: externalize
    private final static Version VERSION = new Version(1, 8, 0, null);

    /*
    /********************************************************************** 
    /* Configuration settings
    /********************************************************************** 
     */

    /**
     * Flag to indicate whether we will try to load generated classes using
     * same class loader as one that loaded class being accessed or not.
     * If not, we will use class loader that loaded this module.
     * Benefit of using value class loader is that 'protected' and 'package access'
     * properties can be accessed; otherwise only 'public' properties can
     * be accessed.
     *<p>
     * By default this feature is enabled.
     */
    protected boolean _cfgUseValueClassLoader = true;
    
    /*
    /********************************************************************** 
    /* Basic life-cycle
    /********************************************************************** 
     */
    
    public AfterburnerModule()
    {
        super("Afterburner", VERSION);
    }
    
    @Override
    public void setupModule(SetupContext context)
    {
        super.setupModule(context);
        ClassLoader cl = _cfgUseValueClassLoader ? null : getClass().getClassLoader();
        context.addBeanDeserializerModifier(new DeserializerModifier(cl));
        context.addBeanSerializerModifier(new SerializerModifier(cl));
    }

    /*
    /********************************************************************** 
    /* Config methods
    /********************************************************************** 
     */

    /**
     * Flag to indicate whether we will try to load generated classes using
     * same class loader as one that loaded class being accessed or not.
     * If not, we will use class loader that loaded this module.
     * Benefit of using value class loader is that 'protected' and 'package access'
     * properties can be accessed; otherwise only 'public' properties can
     * be accessed.
     *<p>
     * By default this feature is enabled.
     */
    public void setUseValueClassLoader(boolean state) {
        _cfgUseValueClassLoader = state;
    }
}
