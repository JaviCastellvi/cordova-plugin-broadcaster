var exec = require('cordova/exec');

exports.receiver = function (success, error) {
    exec(success, error, 'Broadcaster', 'receiver', []);
};
