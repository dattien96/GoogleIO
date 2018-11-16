/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dattien96.vn.shared.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


/**
 * (from: https://docs.oracle.com/javaee/6/api/javax/inject/Scope.html)
 * Theo mặc định , nếu ta không định nghĩa scope cho fun provide, thì injector sẽ tạo ra 1 instance,
 * use nó chỉ cho 1 lần inject và sau đó sẽ quên nó. Nếu dùng scope thì injector có thể giữ lại được
 * thể hiện đó và dùng cho lần sau.
 * @Singleton cũng là 1 loại scope cho phép chỉ provide 1 instance
 *
 * No scope = new instance created every time
 * [@Singleton] = only one instance
 * [@CustomScope] = instance reused depending on the component’s lifecycle
 *
 * --> Như cách viết trên thì ta sẽ thấy @Singleton kkhasc với @ActScope ở chỗ là thể hiện của singleton
 * là duy nhất toàn app. Còn với custom scope này thì nó chỉ là duy nhất trong vòng đời act mà thôi.
 *
 * Với Dagger thì 1 unscoped component sẽ không được định nghĩa dựa vào 1 scoped component.
 * Ví dụ AppComponent đã đc scope với @Singleton. Thì mọi component con của nó cũng phải đc scope.
 * Như trong case này ta có các Custom Scope cho act hay frg, viewmodel
 *
 * Ngoài ra subCom thì k đc có scope giống parentCom. Ví dụ act đã dùng @ActivityScope thì thằng
 * FrgCom k đc dùng scope này nếu ta định nghĩa FrgCom là SubCom của ActCom
 *
 * ActivityScoped sẽ chỉ định sự tồn tại của obj là duy nhất trong vòng đời act
 * [@Singleton] sẽ chỉ định sự tồn tại của obj là duy nhất trong vòng đời Application.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScoped {
}
