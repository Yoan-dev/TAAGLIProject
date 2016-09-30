(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('StageDetailController', StageDetailController);

    StageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stage', 'Adresse', 'Entreprise', 'Responsable', 'Enseignant', 'Filiere', 'Etudiant'];

    function StageDetailController($scope, $rootScope, $stateParams, previousState, entity, Stage, Adresse, Entreprise, Responsable, Enseignant, Filiere, Etudiant) {
        var vm = this;

        vm.stage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taagliProjectApp:stageUpdate', function(event, result) {
            vm.stage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
