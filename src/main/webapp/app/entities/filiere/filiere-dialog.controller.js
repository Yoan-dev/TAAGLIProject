(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('FiliereDialogController', FiliereDialogController);

    FiliereDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Filiere', 'Enseignant'];

    function FiliereDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Filiere, Enseignant) {
        var vm = this;

        vm.filiere = entity;
        vm.clear = clear;
        vm.save = save;
        vm.enseignants = Enseignant.query({filter: 'filiere-is-null'});
        $q.all([vm.filiere.$promise, vm.enseignants.$promise]).then(function() {
            if (!vm.filiere.enseignant || !vm.filiere.enseignant.id) {
                return $q.reject();
            }
            return Enseignant.get({id : vm.filiere.enseignant.id}).$promise;
        }).then(function(enseignant) {
            vm.enseignants.push(enseignant);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.filiere.id !== null) {
                Filiere.update(vm.filiere, onSaveSuccess, onSaveError);
            } else {
                Filiere.save(vm.filiere, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('taagliProjectApp:filiereUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
