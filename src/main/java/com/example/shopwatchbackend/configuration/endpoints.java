package com.example.shopwatchbackend.configuration;

public class endpoints {
    public static final String[] PUBLIC_POST = {
            "api/v1/customers/login",
            "api/v1/customers/register"
    };
    public static final String[] PUBLIC_GET = {
            "api/v1/products",
            "api/v1/products/images/*",
            "api/v1/products/*",
            "api/v1/categories",
            "api/v1/categories/*",
            "api/v1/payments",
            "api/v1/roles"
    };
}
