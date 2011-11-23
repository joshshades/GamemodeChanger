package com.mojang.util;


public class OsMap
{
    static final int os[]; /* synthetic field */

    static 
    {
        os = new int[EnumOS.values().length];
        try
        {
            os[EnumOS.linux.ordinal()] = 1;
        }
        catch(NoSuchFieldError nosuchfielderror) { }
        try
        {
            os[EnumOS.solaris.ordinal()] = 2;
        }
        catch(NoSuchFieldError nosuchfielderror1) { }
        try
        {
            os[EnumOS.windows.ordinal()] = 3;
        }
        catch(NoSuchFieldError nosuchfielderror2) { }
        try
        {
            os[EnumOS.macos.ordinal()] = 4;
        }
        catch(NoSuchFieldError nosuchfielderror3) { }
    }
}
