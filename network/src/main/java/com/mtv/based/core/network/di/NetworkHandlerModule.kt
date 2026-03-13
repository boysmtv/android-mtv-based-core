/*
 * Project: Shopme App
 * Author: Boys.mtv@gmail.com
 * File: NetworkHandlerModule.kt
 *
 * Last modified by Dedy Wijaya on 03/03/26 20.53
 */

package com.mtv.based.core.network.di

import com.mtv.based.core.network.handler.DownloadRequestHandler
import com.mtv.based.core.network.handler.JsonRequestHandler
import com.mtv.based.core.network.handler.MultipartRequestHandler
import com.mtv.based.core.network.handler.RequestHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkHandlerModule {

    @Binds
    @IntoSet
    abstract fun bindJsonHandler(
        handler: JsonRequestHandler
    ): RequestHandler

    @Binds
    @IntoSet
    abstract fun bindMultipartHandler(
        handler: MultipartRequestHandler
    ): RequestHandler

    @Binds
    @IntoSet
    abstract fun bindDownloadHandler(
        handler: DownloadRequestHandler
    ): RequestHandler
}