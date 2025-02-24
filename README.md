# GraphicsTask
I completed the test task following my usual approach of structuring code into feature modules. This helps organize the project, isolate logical components, speed up incremental builds, and facilitate parallel development. The trade-offs include increased complexity in module interactions, as well as additional challenges in managing dependencies with Dagger and handling navigation.

I also applied Clean Architecture, which is considered the de facto standard in Android development. The technologies and libraries were chosen based on my experience, but I am comfortable working with alternatives like RxJava.

Some parts of the code could be improved—for example, XYGraphView has grown quite large, and some logic could be extracted into separate classes to limit their responsibilities. However, given the time constraints, I focused on implementing the core functionality.

--------------------------------------

Я выполнил тестовое задание, придерживаясь привычного мне подхода разделения кода на фича-модули. Этот подход помогает структурировать проект, изолировать логические части, ускоряет инкрементальную сборку и облегчает параллельную разработку. Его недостатками могут быть усложнение взаимодействия между модулями, а также дополнительные сложности с управлением зависимостями в Dagger и навигацией.

Также я использовал Clean Architecture, которая является де-факто стандартом в Android-разработке. Выбранные технологии и библиотеки наиболее мне привычны, но я могу работать и с альтернативными инструментами, например, RxJava.

Некоторые части кода можно было бы улучшить — например, XYGraphView разросся, и его логику можно было бы вынести в отдельные классы. Однако, учитывая ограниченное время, я решил сосредоточиться на ключевых аспектах реализации.
