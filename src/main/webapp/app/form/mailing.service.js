(function() {
    'use strict';
    angular
        .module('taagliProjectApp')
        .factory('Mailing', Mailing);

    Mailing.$inject = ['$resource'];

    function Mailing ($resource) {
        var resourceUrl =  '/api/mailing/:data';
        console.log("service");
        console.log();
        return $resource(resourceUrl, {}, {
        	'get': {method:'GET', isArray:true}
        });
    }
})();
