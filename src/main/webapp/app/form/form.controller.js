(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('FormController', FormController);

    FormController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function FormController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
    }
})();
