/**
 * cordova is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) Matt Kane 2010
 * Copyright (c) 2011, IBM Corporation
 */


var exec = cordova.require("cordova/exec");

/**
 * Constructor.
 *
 * @returns {ItosDevice}
 */
function ItosDevice() {
}

/**
 * Print text with Itos Device.
 *
 * @param {Function} successCallback 
 * @param {Function} errorCallback
 * @param data
 */
ItosDevice.prototype.print = function (successCallback, errorCallback, data) {

    if (errorCallback == null) {
        errorCallback = function () {
        };
    }

    if (typeof errorCallback != "function") {
        console.log("ItosDevice.print failure: failure parameter not a function");
        return;
    }

    if (typeof successCallback != "function") {
        console.log("ItosDevice.print failure: success callback parameter must be a function");
        return;
    }

    exec(successCallback, errorCallback, 'ItosDevice', 'print', [
        { "data": data}
    ]);

       
};

/**
 * Beep with Itos Device.
 *
 * @param {Function} successCallback 
 * @param {Function} errorCallback
 * @param seconds
 */

ItosDevice.prototype.beep = function (successCallback, errorCallback, seconds) {

    if (errorCallback == null) {
        errorCallback = function () {
        };
    }

    if (typeof errorCallback != "function") {
        console.log("ItosDevice.beep failure: failure parameter not a function");
        return;
    }

    if (typeof successCallback != "function") {
        console.log("ItosDevice.beep failure: success callback parameter must be a function");
        return;
    }

    exec(successCallback, errorCallback, 'ItosDevice', 'beep', [
        { "seconds": seconds}
    ]);

       
};



/**
 * Change leds status
 *
 * @param {Function} successCallback 
 * @param {Function} errorCallback
 * @param color
 * @param isOn
 * 
 */
ItosDevice.prototype.light = function (successCallback, errorCallback, color, isOn) {

    if (errorCallback == null) {
        errorCallback = function () {
        };
    }

    if (typeof errorCallback != "function") {
        console.log("ItosDevice.light failure: failure parameter not a function");
        return;
    }

    if (typeof successCallback != "function") {
        console.log("ItosDevice.light failure: success callback parameter must be a function");
        return;
    }

    exec(successCallback, errorCallback, 'ItosDevice', 'light', [
        { "color": color, "isOn": isOn}
    ]);

       
};

var itosDevice = new ItosDevice();
console.log('Itos Device Plugin Loaded');
module.exports = itosDevice;
