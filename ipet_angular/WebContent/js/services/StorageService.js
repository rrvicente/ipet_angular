(function () {
    'use strict';

    angular
        .module('app')
        .factory('StorageService', StorageService);

    function StorageService() {

        var data = {};

        function storage(value) {
            data = value
        }

        function getStorage(id) {
            return data;
        }
    }
})();