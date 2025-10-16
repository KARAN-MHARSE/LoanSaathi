package com.aurionpro.loanapp.util;

public class DefaultProfileImageUtil {

	 private static final String DEFAULT_PROFILE_URL = 
		        "https://res.cloudinary.com/dgottdgvo/image/upload/v1760593937/WhatsApp_Image_2025-10-16_at_11.19.31_AM_ompwne.jpg";

		    private DefaultProfileImageUtil() {
		        // prevent instantiation
		    }

		    public static String getDefaultProfileUrl() {
		        return DEFAULT_PROFILE_URL;
		    }
}
