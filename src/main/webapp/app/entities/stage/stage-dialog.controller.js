(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('StageDialogController', StageDialogController);

    StageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Stage', 'Adresse', 'Entreprise', 'Responsable', 'Enseignant', 'Filiere', 'Etudiant'];

    function StageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Stage, Adresse, Entreprise, Responsable, Enseignant, Filiere, Etudiant) {
        var vm = this;

        vm.stage = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.adresses = Adresse.query();
        vm.entreprises = Entreprise.query();
        vm.responsables = Responsable.query();
        vm.enseignants = Enseignant.query();
        vm.filieres = Filiere.query();
        vm.etudiants = Etudiant.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.stage.id !== null) {
                Stage.update(vm.stage, onSaveSuccess, onSaveError);
            } else {
                Stage.save(vm.stage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('taagliProjectApp:stageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateDebut = false;
        vm.datePickerOpenStatus.dateFin = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
