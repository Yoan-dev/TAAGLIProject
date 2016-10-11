(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('FormController', FormController);

    FormController.$inject = ['$scope'];

    function FormController ($scope, Principal, LoginService, $state) {
        var vm = this;

    }
})();
