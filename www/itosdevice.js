/**
 * cordova is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) Matt Kane 2010
 * Copyright (c) 2011, IBM Corporation
 */


var exec = cordova.require("cordova/exec");

var scanInProgress = false;

/**
 * Constructor.
 *
 * @returns {BarcodeScanner}
 */
function ItosDevice() {
}

/**
 * Read code from scanner.
 *
 * @param {Function} successCallback This function will recieve a result object: {
         *        text : '12345-mock',    // The code that was scanned.
         *        format : 'FORMAT_NAME', // Code format.
         *        cancelled : true/false, // Was canceled.
         *    }
 * @param {Function} errorCallback
 * @param config
 */
ItosDevice.prototype.print = function (successCallback, errorCallback, config) {

        if (config instanceof Array) {
            // do nothing
        } else {
            if (typeof(config) === 'object') {
                config = [ config ];
            } else {
                config = [];
            }
        }

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

        if (scanInProgress) {
            errorCallback('Scan is already in progress');
            return;
        }

        scanInProgress = true;


        exec(
            function(result) {
                scanInProgress = false;
                successCallback(result);
            },
            function(error) {
                scanInProgress = false;
                errorCallback(error);
            },
            'ItosDevice',
            'print',
            config
        );
    };

var itosDevice = new ItosDevice();
console.log('Itos Device Plugin Loaded');
module.exports = itosDevice;
