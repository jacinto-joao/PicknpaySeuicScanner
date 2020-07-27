var exec = require("cordova/exec");

module.exports.coolMethod = function (arg0, success, error) {
  exec(success, error, "PicknpaySeuicScanner", "coolMethod", [arg0]);
};

module.exports.openScanner = function (arg0, success, error) {
  exec(success, error, "PicknpaySeuicScanner", "openScanner", [arg0]);
};

module.exports.closeScanner = function (arg0, success, error) {
  exec(success, error, "PicknpaySeuicScanner", "closeScanner", [arg0]);
};

module.exports.startScan = function (arg0, success, error) {
  exec(success, error, "PicknpaySeuicScanner", "startScan", [arg0]);
};
module.exports.setDecodeCallback = function (arg0, success, error) {
  exec(success, error, "PicknpaySeuicScanner", "setDecodeCallback", [arg0]);
};
