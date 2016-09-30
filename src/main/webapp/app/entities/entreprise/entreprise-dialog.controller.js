(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .controller('EntrepriseDialogController', EntrepriseDialogController);

    EntrepriseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Entreprise', 'Adresse'];

    function EntrepriseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Entreprise, Adresse) {
        var vm = this;

        vm.entreprise = entity;
        vm.clear = clear;
        vm.save = save;
        vm.adresses = Adresse.query({filter: 'entreprise-is-null'});
        $q.all([vm.entreprise.$promise, vm.adresses.$promise]).then(function() {
            if (!vm.entreprise.adresse || !vm.entreprise.adresse.id) {
                return $q.reject();
            }
            return Adresse.get({id : vm.entreprise.adresse.id}).$promise;
        }).then(function(adresse) {
            vm.adresses.push(adresse);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entreprise.id !== null) {
                Entreprise.update(vm.entreprise, onSaveSuccess, onSaveError);
            } else {
                Entreprise.save(vm.entreprise, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('taagliProjectApp:entrepriseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
