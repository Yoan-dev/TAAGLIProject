(function() {
    'use strict';
    angular
        .module('taagliProjectApp')
        .factory('Form', Form);

    Form.$inject = ['$resource'];

    function Form ($resource) {
        var resourceUrl =  '/api/form/:data';
        console.log("service");
        return $resource(resourceUrl, {}, {
        	'get': {method:'GET', isArray:true}
        });
    }
})();
