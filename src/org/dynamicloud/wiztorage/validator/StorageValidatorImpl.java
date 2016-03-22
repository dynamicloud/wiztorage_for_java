package org.dynamicloud.wiztorage.validator;

import org.dynamicloud.api.DynamicProvider;
import org.dynamicloud.api.DynamicProviderImpl;
import org.dynamicloud.api.Query;
import org.dynamicloud.api.RecordResults;
import org.dynamicloud.api.criteria.Conditions;
import org.dynamicloud.api.model.RecordModel;
import org.dynamicloud.exception.DynamicloudProviderException;
import org.dynamicloud.wiztorage.DynamicloudCredentials;
import org.dynamicloud.wiztorage.exception.RuntimeWiztorageException;
import org.dynamicloud.wiztorage.processor.UploadBean;

/**
 * Copyright (c) 2016 Dynamicloud
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * <code>StorageValidatorImpl</code>
 * <p/>
 * This class provides a set of methods to validate records, execute validations, etc
 *
 * @author Eleazar Gomez
 */
public class StorageValidatorImpl implements StorageValidator {
    public DynamicloudCredentials credentials;

    /**
     * Creates an object with the DynamicloudCredentials
     * @param credentials DynamicloudCredentials
     */
    public StorageValidatorImpl(DynamicloudCredentials credentials) {
        this.credentials = credentials;
    }

    /**
     * This method validates if a filename already exists in Dynamicloud
     *
     * @param fileName to validate
     * @return true if already exists.
     */
    public boolean existsFileName(String fileName) {
        DynamicProvider<UploadBean> provider = new DynamicProviderImpl<UploadBean>(this.credentials);

        RecordModel recordModel = new RecordModel(this.credentials.getModelIdentifier());
        recordModel.setBoundClass(UploadBean.class);

        Query<UploadBean> query = provider.createQuery(recordModel);
        query.add(Conditions.and(Conditions.equals("name", fileName), Conditions.equals("checked", 1)));

        query.setProjection("count(*) as count");

        try {
            RecordResults<UploadBean> list = query.list();
            return list.getRecords().get(0).getCount() > 0;
        } catch (DynamicloudProviderException e) {
            throw new RuntimeWiztorageException(e.getMessage());
        }
    }
}