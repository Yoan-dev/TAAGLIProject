(function() {
    'use strict';
    angular
        .module('taagliProjectApp')
        .factory('Form', Form);

    Form.$inject = ['$resource'];

    function Form ($resource) {
        var resourceUrl =  '/api/form/:data';

        return $resource(resourceUrl);
    }
})();
