/* globals require */

/*!
 * Module dependencies.
 */

var cordova = require('./helper/cordova'),
    ItosDevice = require('../www/itosdevice'),
    execSpy,
    execWin,
    options;

/*!
 * Specification.
 */

describe('phonegap-plugin-itosdevice', function () {
    beforeEach(function () {
        execWin = jasmine.createSpy();
        execSpy = spyOn(cordova.required, 'cordova/exec').andCallFake(execWin);
    });

    describe('ItosDevice', function () {
      it("ItosDevice plugin should exist", function() {
          expect(ItosDevice).toBeDefined();
          expect(typeof ItosDevice == 'object').toBe(true);
      });

      it("should contain a print function", function() {
          expect(ItosDevice.print).toBeDefined();
          expect(typeof ItosDevice.print == 'function').toBe(true);
      });
    });

    describe('ItosDevice instance', function () {
        describe('cordova.exec', function () {
            it('should call cordova.exec on next process tick', function (done) {
                ItosDevice.print(function() {}, function() {}, {});
                setTimeout(function () {
                    expect(execSpy).toHaveBeenCalledWith(
                        jasmine.any(Function),
                        jasmine.any(Function),
                        'ItosDevice',
                        'print',
                        jasmine.any(Object)
                    );
                    done();
                }, 100);
            });
        });
    });
});
