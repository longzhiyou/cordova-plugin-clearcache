var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'CordovaClearCache', 'coolMethod', [arg0]);
};

exports.clearExternalCache = function (arg0, success, error) {
    exec(success, error, 'CordovaClearCache', 'clearCacheAndExternalCache', [arg0]);
};

exports.webViewClearCache = function (arg0, success, error) {
    exec(success, error, 'CordovaClearCache', 'webViewClearCache', [arg0]);
};

exports.clearCacheOnly = function (arg0, success, error) {
    exec(success, error, 'CordovaClearCache', 'clearCacheOnly', [arg0]);
};
