(function() {
    'use strict';

    angular
        .module('taagliProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('adresse', {
            parent: 'entity',
            url: '/adresse?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Adresses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/adresse/adresses.html',
                    controller: 'AdresseController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('adresse-detail', {
            parent: 'entity',
            url: '/adresse/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Adresse'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/adresse/adresse-detail.html',
                    controller: 'AdresseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Adresse', function($stateParams, Adresse) {
                    return Adresse.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'adresse',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('adresse-detail.edit', {
            parent: 'adresse-detail',
            url: '/detail/edit',
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
                        entity: ['Adresse', function(Adresse) {
                            return Adresse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('adresse.new', {
            parent: 'adresse',
            url: '/new',
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
                    $state.go('adresse', null, { reload: 'adresse' });
                }, function() {
                    $state.go('adresse');
                });
            }]
        })
        .state('adresse.edit', {
            parent: 'adresse',
            url: '/{id}/edit',
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
                        entity: ['Adresse', function(Adresse) {
                            return Adresse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('adresse', null, { reload: 'adresse' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('adresse.delete', {
            parent: 'adresse',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/adresse/adresse-delete-dialog.html',
                    controller: 'AdresseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Adresse', function(Adresse) {
                            return Adresse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('adresse', null, { reload: 'adresse' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
