var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'PicknpaySeuicScanner', 'coolMethod', [arg0]);
};


exports.openScanner = function () {
    exec(null, null, "PicknpaySeuicScanner", "openScanner", []);
};

exports.closeScanner = function () {
    exec(null, null, "PicknpaySeuicScanner", "closeScanner", []);
};

exports.startScan = function () {
    exec(null, null, "PicknpaySeuicScanner", "startScan", []);
};
exports.setDecodeCallback = function () {
    exec(null, null, "PicknpaySeuicScanner", "setDecodeCallback", []);
};
