
package one.microstream.storage;

import one.microstream.enterprise.afs.aws.s3.types.S3Connector;
import one.microstream.enterprise.afs.blobstore.types.BlobStoreFileSystem;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import software.amazon.awssdk.services.s3.S3Client;


public final class DB
{
	public static EmbeddedStorageManager	storageManager;
	public final static DataRoot			root	= new DataRoot();
	
	static
	{
		S3Client client = S3Utils.getClient();
		
		BlobStoreFileSystem fileSystem = BlobStoreFileSystem.New(
			S3Connector.Caching(client));
		
		String S3bucketname = "storage-categoryservice";
		storageManager = EmbeddedStorage.start(root, fileSystem.ensureDirectoryPath(S3bucketname));
	}
		
}
