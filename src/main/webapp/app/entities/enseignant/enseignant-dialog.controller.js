(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('EnseignantDialogController', EnseignantDialogController);

    EnseignantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Enseignant'];

    function EnseignantDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Enseignant) {
        var vm = this;

        vm.enseignant = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.enseignant.id !== null) {
                Enseignant.update(vm.enseignant, onSaveSuccess, onSaveError);
            } else {
                Enseignant.save(vm.enseignant, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('taagliProjectApp:enseignantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
