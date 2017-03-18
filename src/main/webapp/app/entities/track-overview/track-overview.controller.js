(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TrackOverviewController', StudentOverviewController);

    StudentOverviewController.$inject = ['$scope', '$state', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function StudentOverviewController ($scope, $state, ParseLinks, AlertService, paginationConstants, pagingParams) {
        let vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        vm.trackInfo = {};
        vm.trackInfo.name = "JAVA Course";
        vm.trackInfo.description = "Some skill description. Before I go into details on how to screen Java skills first let me share some basics that will allow you to become more familiar with this technology.        Firstly, Java world changes often, so experience with framework gained 5 years ago is not valid anymore. Bare this in mind when you assess Java skills based on so far employment. Secondly, in Java world ecosystem knowledge of tools and libraries is more valuable than knowledge of the language itself. Without it a programmer will write everything by himself from scratch and just waste time. Last but not least only commercial experience is important. Java knowledge from university is hardly ever useful for business coding. So unless you recruit for an entry, junior position you can skip assessing Java skills based solely on university education and degree. In that case what is more important is candidate’s real life coding projects, it doesn’t have to be a commercial one – it can be hobby or open source programming project.";

        vm.trackInfo.faculty = "OIT";

        vm.trackInfo.skills = [
            {
                name: "JAVA Enterprise",
                description: "Java EE - Java Platform, Enterprise Edition (Java EE) is the standard in community-driven enterprise software. Java EE is developed using the Java Community Process, with contributions from industry experts, commercial and open source organizations, Java User Groups, and countless individuals. Each release integrates new features that align with industry needs, improves application portability, and increases developer productivity. Today, Java EE offers a rich enterprise software platform and with over 20 compliant Java EE implementations to choose from, low risk and plenty of options.",
                rating: 65
            },
            {
                name: "JAVA Spring",
                description: "Spring Projects. From configuration to security, web apps to big data – whatever the infrastructure needs of your application may be, there is a Spring Project to help you build it. Start small and use just what you need – Spring is modular by design.",
                rating: 84
            }
        ];

        vm.trackInfo.teaching = {
            instructors: [
                {
                    name : "Mike Surname1",
                    chair: "TK",
                    description: "This teacher creates a welcoming learning environment for all students.",
                    img: "http://s020.radikal.ru/i712/1703/91/405c60fa613c.png"
                },
                {
                    name : "Alice Surname2",
                    chair: "PBF",
                    description: "A great teacher lets students know that they can depend not only on her, but also on the entire class.",
                    img: "http://s018.radikal.ru/i520/1703/d7/22c7278c8e72.png"
                },
                {
                    name : "Bob Surname3",
                    chair: "TEF",
                    description: "This is the teacher to whom students know they can go with any problems or concerns or even to share a funny story.",
                    img: "http://s02.radikal.ru/i175/1703/f1/fd56e568c824.jpg"
                }
            ]
        };

        vm.selectedInstructor = vm.trackInfo.teaching.instructors[0];
        vm.isNewDesign = true;

        if (vm.trackInfo.skills.length > 0)
            vm.selectedSkill = vm.trackInfo.skills[0];


        vm.getOverallRating = function () {
            let trackRating = 0;
            for (let i=0; i < vm.trackInfo.skills.length; i++){
                trackRating += vm.trackInfo.skills[i].rating;
            }
            let overallRating = trackRating / vm.trackInfo.skills.length;
            return overallRating;
        };



        function loadAll () {

            function sort() {
                let result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.studentInfos = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
