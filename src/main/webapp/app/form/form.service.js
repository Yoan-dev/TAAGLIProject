(function() {
    'use strict';
    angular
        .module('taagliProjectApp')
        .factory('Form', Form);

    Form.$inject = ['$resource'];

    function Form ($resource) {
        var resourceUrl =  '/api/form/:data';

        return $resource(resourceUrl, {}, {
        	'get': { 
        		method:'GET',
        		transformResponse: function(data) {
        			if (data) {
        				data = angular.fromJson(data);
        			}
        			return data;
        		}
        	}
        });
    }
})();
