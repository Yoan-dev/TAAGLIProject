(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('convention', {
            parent: 'app',
            url: '/convention',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Convention'
            },
            views: {
                'content@': {
                    templateUrl: 'app/convention/convention.html',
                    controller: 'ConventionController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('convention.adresse', {
            parent: 'convention',
            url: '/newAdresse',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/adresse/adresse-dialog.html',
                    controller: 'AdresseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                libelle: null,
                                codePostal: null,
                                ville: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('^', null, null);
                }, function() {
                    $state.go('^');
                });
            }],
            onExit: ['$rootScope', function($rootScope){
                $rootScope.$broadcast('reloadAdresses');
            }]
        })
        .state('convention.entreprise', {
            parent: 'convention',
            url: '/newEntreprise',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entreprise/entreprise-dialog.html',
                    controller: 'EntrepriseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                siret: null,
                                telephone: null,
                                mail: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('^', null, null);
                }, function() {
                    $state.go('^');
                });
            }],
            onExit: ['$rootScope', function($rootScope){
                $rootScope.$broadcast('reloadEntreprises');
            }]
        })
        .state('convention.responsable', {
            parent: 'convention',
            url: '/newResponsable',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsable/responsable-dialog.html',
                    controller: 'ResponsableDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                prenom: null,
                                poste: null,
                                telephone: null,
                                mail: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('^', null, null);
                }, function() {
                    $state.go('^');
                });
            }],
            onExit: ['$rootScope', function($rootScope){
                $rootScope.$broadcast('reloadResponsables');
            }]
        })
        .state('convention.enseignant', {
            parent: 'convention',
            url: '/newEnseignant',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enseignant/enseignant-dialog.html',
                    controller: 'EnseignantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                prenom: null,
                                telephone: null,
                                mail: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('^', null, null);
                }, function() {
                    $state.go('^');
                });
            }],
            onExit: ['$rootScope', function($rootScope){
                $rootScope.$broadcast('reloadEnseignants');
            }]
        })
        .state('convention.filiere', {
            parent: 'convention',
            url: '/newFiliere',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filiere/filiere-dialog.html',
                    controller: 'FiliereDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('^', null, null);
                }, function() {
                    $state.go('^');
                });
            }],
            onExit: ['$rootScope', function($rootScope){
                $rootScope.$broadcast('reloadFilieres');
            }]
        })
        .state('convention.etudiant', {
            parent: 'convention',
            url: '/newEtudiant',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/etudiant/etudiant-dialog.html',
                    controller: 'EtudiantDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                prenom: null,
                                numeroEtu: null,
                                telephone: null,
                                mail: null,
                                dateDeNaissance: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('^', null, null);
                }, function() {
                    $state.go('^');
                });
            }],
            onExit: ['$rootScope', function($rootScope){
                $rootScope.$broadcast('reloadEtudiants');
            }]
        });
    }
})();
