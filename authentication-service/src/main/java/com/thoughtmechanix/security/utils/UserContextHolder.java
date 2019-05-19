package com.thoughtmechanix.security.utils;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static final UserContext getUserContext(){
        UserContext context = userContext.get();

        if(context == null){
            context = createEmptyContext();
            userContext.set(context);
        }
        return userContext.get();
    }

    public static final void setContext(UserContext context){
        if(context != null) {
            userContext.set(context);
        }
    }


    private static UserContext createEmptyContext() {
        return new UserContext();
    }
}
