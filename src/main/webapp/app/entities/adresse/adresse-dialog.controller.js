(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('AdresseDialogController', AdresseDialogController);

    AdresseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Adresse'];

    function AdresseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Adresse) {
        var vm = this;

        vm.adresse = entity;
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
            if (vm.adresse.id !== null) {
                Adresse.update(vm.adresse, onSaveSuccess, onSaveError);
            } else {
                Adresse.save(vm.adresse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('taagliProjectApp:adresseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
