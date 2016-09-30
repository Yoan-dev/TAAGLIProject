(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('EntrepriseDetailController', EntrepriseDetailController);

    EntrepriseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entreprise', 'Adresse'];

    function EntrepriseDetailController($scope, $rootScope, $stateParams, previousState, entity, Entreprise, Adresse) {
        var vm = this;

        vm.entreprise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taagliProjectApp:entrepriseUpdate', function(event, result) {
            vm.entreprise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
