# cordova-plugin-clearcache
clear externalCacheDir and cacheDir or webView cache

### install

~~~
cordova plugin add https://github.com/jeryM/cordova-plugin-clearcache.git
~~~
### use
~~~
ionic :
declare let CordovaClearCache;
*** clear externalCacheDir and cacheDir
CordovaClearCache.clearCacheAndExternalCache();

*** Clears the resource cache. Note that the cache is per-application, so this will clear the cache for all WebViews used
CordovaClearCache.webViewClearCache();

*** clear cacheDir from cordova.getActivity().getCacheDir()
CordovaClearCache.clearCacheOnly();
~~~

