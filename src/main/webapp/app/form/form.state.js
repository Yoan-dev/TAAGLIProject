(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('form', {
            parent: 'app',
            url: '/form',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Form'
            },
            views: {
                'content@': {
                    templateUrl: 'app/form/form.html',
                    controller: 'FormController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
