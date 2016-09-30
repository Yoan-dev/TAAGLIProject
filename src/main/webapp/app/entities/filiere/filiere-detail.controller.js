(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('FiliereDetailController', FiliereDetailController);

    FiliereDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Filiere', 'Enseignant'];

    function FiliereDetailController($scope, $rootScope, $stateParams, previousState, entity, Filiere, Enseignant) {
        var vm = this;

        vm.filiere = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taagliProjectApp:filiereUpdate', function(event, result) {
            vm.filiere = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
